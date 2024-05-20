import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { IngestionRequestDetailsUpdateComponent } from 'app/entities/ingestion-request-details/ingestion-request-details-update.component';
import { IngestionRequestDetailsService } from 'app/entities/ingestion-request-details/ingestion-request-details.service';
import { IngestionRequestDetails } from 'app/shared/model/ingestion-request-details.model';

describe('Component Tests', () => {
  describe('IngestionRequestDetails Management Update Component', () => {
    let comp: IngestionRequestDetailsUpdateComponent;
    let fixture: ComponentFixture<IngestionRequestDetailsUpdateComponent>;
    let service: IngestionRequestDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [IngestionRequestDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IngestionRequestDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IngestionRequestDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IngestionRequestDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IngestionRequestDetails(123);
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
        const entity = new IngestionRequestDetails();
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
