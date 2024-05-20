import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetDetailsUpdateComponent } from 'app/entities/dataset-details/dataset-details-update.component';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';
import { DatasetDetails } from 'app/shared/model/dataset-details.model';

describe('Component Tests', () => {
  describe('DatasetDetails Management Update Component', () => {
    let comp: DatasetDetailsUpdateComponent;
    let fixture: ComponentFixture<DatasetDetailsUpdateComponent>;
    let service: DatasetDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DatasetDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DatasetDetails(123);
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
        const entity = new DatasetDetails();
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
