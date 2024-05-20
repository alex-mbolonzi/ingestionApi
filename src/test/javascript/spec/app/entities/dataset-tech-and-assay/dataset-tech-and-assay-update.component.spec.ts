import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetTechAndAssayUpdateComponent } from 'app/entities/dataset-tech-and-assay/dataset-tech-and-assay-update.component';
import { DatasetTechAndAssayService } from 'app/entities/dataset-tech-and-assay/dataset-tech-and-assay.service';
import { DatasetTechAndAssay } from 'app/shared/model/dataset-tech-and-assay.model';

describe('Component Tests', () => {
  describe('DatasetTechAndAssay Management Update Component', () => {
    let comp: DatasetTechAndAssayUpdateComponent;
    let fixture: ComponentFixture<DatasetTechAndAssayUpdateComponent>;
    let service: DatasetTechAndAssayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetTechAndAssayUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DatasetTechAndAssayUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetTechAndAssayUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetTechAndAssayService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatasetTechAndAssay(123);
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
        const entity = new DatasetTechAndAssay();
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
