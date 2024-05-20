import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetDataCategoryUpdateComponent } from 'app/entities/dataset-data-category/dataset-data-category-update.component';
import { DatasetDataCategoryService } from 'app/entities/dataset-data-category/dataset-data-category.service';
import { DatasetDataCategory } from 'app/shared/model/dataset-data-category.model';

describe('Component Tests', () => {
  describe('DatasetDataCategory Management Update Component', () => {
    let comp: DatasetDataCategoryUpdateComponent;
    let fixture: ComponentFixture<DatasetDataCategoryUpdateComponent>;
    let service: DatasetDataCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetDataCategoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DatasetDataCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetDataCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetDataCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatasetDataCategory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatasetDataCategory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
